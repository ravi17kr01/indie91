package controllers

import (
	"fmt"
	"log"
	"os"
	"time"
	"encoding/json"
	"supabase-fiber-app/config"
	"supabase-fiber-app/utils"
	"supabase-fiber-app/models"
	"gorm.io/gorm"
	"github.com/google/uuid"

	"github.com/gofiber/fiber/v2"
	openapi "github.com/twilio/twilio-go/rest/api/v2010"
	verify "github.com/twilio/twilio-go/rest/verify/v2"
)

type OTPRequest struct {
	Phone string `json:"phone"`
}

type VerifyRequest struct {
	Phone string `json:"phone"`
	OTP   string `json:"otp"`
}

// SendOTP handles OTP request via Twilio and ensures user exists.
func SendOTP(c *fiber.Ctx) error {
	var req OTPRequest

	// Step 1: Parse incoming request body
	if err := c.BodyParser(&req); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid payload"})
	}

	// Step 2: Create or fetch the user
	user, err := createUserIfNotExists(req.Phone)
	if err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "User creation failed"})
	}

	// Step 3: Prepare Twilio OTP parameters
	params := &verify.CreateVerificationParams{}
	params.SetTo(req.Phone)
	params.SetChannel("sms") // or "call"

	// Optional: Log params for debugging
	if data, err := json.MarshalIndent(params, "", "  "); err == nil {
		log.Println("Twilio Params:\n", string(data))
	}

	// Step 4: Send OTP via Twilio
	resp, err := config.TwilioClient.VerifyV2.CreateVerification(os.Getenv("TWILIO_VERIFY_SID"), params)
	if err != nil {
		log.Println("Twilio Error:", err.Error())
		return c.Status(500).JSON(fiber.Map{"error": "Failed to send OTP"})
	}

	// Step 5: Return success with SID and user ID
	return c.JSON(fiber.Map{
		"message": "OTP sent successfully",
		"sid":     *resp.Sid,
		"userId":  user.ID,
	})
}


// VerifyOTP checks if the user-entered OTP is valid using Twilio and returns user info.
func VerifyOTP(c *fiber.Ctx) error {
	var req VerifyRequest

	// Step 1: Parse request body
	if err := c.BodyParser(&req); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid payload"})
	}

	// Step 2: Set verification parameters for Twilio
	params := &verify.CreateVerificationCheckParams{}
	params.SetTo(req.Phone)
	params.SetCode(req.OTP)

	// Step 3: Validate OTP using Twilio Verify API
	resp, err := config.TwilioClient.VerifyV2.CreateVerificationCheck(os.Getenv("TWILIO_VERIFY_SID"), params)
	if err != nil || *resp.Status != "approved" {
		return c.Status(401).JSON(fiber.Map{"error": "OTP verification failed"})
	}

	// Step 4: Ensure user exists (will also create if someone skipped SendOTP)
	user, err := createUserIfNotExists(req.Phone)
	if err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "User fetch failed"})
	}

	// Step 5: Return success with user ID
	return c.JSON(fiber.Map{
		"message": "OTP verified successfully",
		"userId":  user.ID,
	})
}


// createUserIfNotExists checks if a user exists based on the phone number.
// If not, it creates a new user and returns the user object.
func createUserIfNotExists(phone string) (*models.AppUser, error) {
	var user models.AppUser

	// Step 1: Search user by phone number
	result := config.DB.Where("phone = ?", phone).First(&user)

	// Step 2: If user is not found, create a new one
	if result.Error != nil {
		if result.Error == gorm.ErrRecordNotFound {
			newUser := models.AppUser{
				ID:          uuid.New().String(),   // Generate a new UUID
				Phone:       phone,                 // Assign provided phone number
				JoinedOn:    time.Now(),            // Set join time
				FirebaseUID: ptr("otp_sent"),       // Mark as otp_sent by default
			}

			// Step 3: Insert new user into the DB
			if err := config.DB.Create(&newUser).Error; err != nil {
				return nil, err
			}

			// Step 4: Return newly created user
			return &newUser, nil
		}

		// Some other DB error
		return nil, result.Error
	}

	// Step 5: Return existing user
	return &user, nil
}

func ptr(s string) *string {
	return &s
}

func SendOTPProduction(c *fiber.Ctx) error {
	var req OTPRequest
	if err := c.BodyParser(&req); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid payload"})
	}

	otp, err := utils.GenerateOTP()
	if err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to generate OTP"})
	}

	// err = utils.SaveOTP(req.Phone, otp)
	// if err != nil {
	// 	return c.Status(500).JSON(fiber.Map{"error": "Failed to store OTP"})
	// }

	msg := fmt.Sprintf("Your OTP is: %s", otp)
	params := &openapi.CreateMessageParams{}
	params.SetTo(req.Phone)
	params.SetFrom(os.Getenv("TWILIO_PHONE")) // Replace with your Twilio number
	params.SetBody(msg)

	_, err = config.TwilioClient.Api.CreateMessage(params)
	if err != nil {
		fmt.Println("Error while sending OTP>>>>>>>>>>>>>>>>>>>>>>>P", err)
		return c.Status(500).JSON(fiber.Map{"error": "Failed to send SMS"})
	}

	return c.JSON(fiber.Map{"message": "OTP sent successfully"})
}

func VerifyOTPProduction(c *fiber.Ctx) error {
	var req VerifyRequest
	if err := c.BodyParser(&req); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid payload"})
	}

	// if utils.VerifyOTP(req.Phone, req.OTP) {
	// 	return c.JSON(fiber.Map{"message": "OTP verified successfully"})
	// }
	return c.Status(401).JSON(fiber.Map{"error": "Invalid OTP"})
}
