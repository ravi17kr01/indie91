package controllers

import (
	"bytes"
	"crypto/sha256"
	"encoding/base64"
	"encoding/hex"
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"os"
	"time"
	"log"

	"github.com/gofiber/fiber/v2"
)

type PhonePeRequest struct {
	MerchantId string `json:"merchantId"`
	MerchantTransactionId string `json:"merchantTransactionId"`
	Amount int `json:"amount"`
	MerchantUserId string `json:"merchantUserId"`
	RedirectUrl string `json:"redirectUrl"`
	CallbackUrl string `json:"callbackUrl"`
	PaymentInstrument     map[string]interface{} `json:"paymentInstrument"`
}

type PhonePePayload struct {
	Request PhonePeRequest `json:"request"`
}

func generateTransactionID() string {
	return fmt.Sprintf("TXN_%d", time.Now().UnixNano())
}

func InitiatePayment(c *fiber.Ctx) error {
	transactionID := generateTransactionID()

	reqPayload := PhonePeRequest{
		MerchantId:     os.Getenv("MERCHANT_ID"),
		MerchantTransactionId:  transactionID,
		Amount:         10000, // ₹100.00 in paisa
		MerchantUserId: "USER123",
		RedirectUrl:    os.Getenv("REDIRECT_URL"),
		CallbackUrl:    os.Getenv("CALLBACK_URL"),
		PaymentInstrument: map[string]interface{}{
		"type": "PAY_PAGE",
	},
	}

	// Step 1: Marshal actual payload
	jsonPayload, _ := json.Marshal(reqPayload)

	// Step 2: Base64 encode
	base64Payload := base64.StdEncoding.EncodeToString(jsonPayload)

	// Step 3: Generate checksum
	saltKey := os.Getenv("SALT_KEY")
	saltIndex := os.Getenv("SALT_INDEX")
	stringToHash := base64Payload + "/pg/v1/pay" + saltKey

	hash := sha256.Sum256([]byte(stringToHash))
	checksum := hex.EncodeToString(hash[:])

	// Step 4: Build request body
	requestBody := map[string]string{
		"request": base64Payload,
	}
	bodyBytes, _ := json.Marshal(requestBody)

	// Step 5: Create HTTP POST request
	url := os.Getenv("BASE_URL") + "/pg/v1/pay"
	req, err := http.NewRequest("POST", url, bytes.NewBuffer(bodyBytes))
	if err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to build request"})
	}
	req.Header.Set("Content-Type", "application/json")
	req.Header.Set("X-VERIFY", checksum+"###"+saltIndex)

	// Step 6: Perform request
	client := &http.Client{}
	res, err := client.Do(req)
	if err != nil {
		fmt.Println("Error sending to PhonePe >>>", err)
		return c.Status(500).JSON(fiber.Map{"error": "Failed to initiate payment"})
	}
	defer res.Body.Close()

	// Step 7: Read response
	respBody, _ := io.ReadAll(res.Body)
	fmt.Println("Response from PhonePe >>>", string(respBody))
	return c.Status(res.StatusCode).Send(respBody)
}


func PaymentCallback(c *fiber.Ctx) error {
	var body map[string]interface{}
	fmt.Println("Got the phonepe callback reponse>>>>>>>>>>>>>>>>>>>>>>>>>")
	if err := c.BodyParser(&body); err != nil {
		log.Println("Error in phonepe callback", err)
		return c.Status(400).JSON(fiber.Map{"error": "Invalid callback payload"})
	}
	// TODO: Verify checksum if needed

	// Save payment status or update DB here
	fmt.Println("Callback received:", body)

	return c.SendStatus(200)
}
