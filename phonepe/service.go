package phonepe

import (
    "bytes"
    "encoding/json"
    "net/http"
)

type PayRequest struct {
    MerchantId            string            `json:"merchantId"`
    MerchantTransactionId string            `json:"merchantTransactionId"`
    MerchantUserId        string            `json:"merchantUserId"`
    Amount                int64             `json:"amount"`
    RedirectUrl           string            `json:"redirectUrl"`
    RedirectMode          string            `json:"redirectMode"`
    CallbackUrl           string            `json:"callbackUrl"`
    MobileNumber          string            `json:"mobileNumber"`
    PaymentInstrument     map[string]string `json:"paymentInstrument"`
}

type PayResponse struct {
    Success bool `json:"success"`
    Code    string `json:"code"`
    Message string `json:"message"`
    Data    json.RawMessage `json:"data"`
}

func InitiatePayment(reqBody PayRequest) (*PayResponse, error) {
    bodyJSON, err := json.Marshal(reqBody)
    if err != nil {
        return nil, err
    }

    encoded := EncodeRequest(bodyJSON)
    xVerify := GenerateXVerify(encoded)

    payload := map[string]string{"request": encoded}
    payloadJSON, _ := json.Marshal(payload)

    client := &http.Client{}
    url := EnvBaseURL + PayEndpoint
    req, err := http.NewRequest("POST", url, bytes.NewBuffer(payloadJSON))
    if err != nil {
        return nil, err
    }

    req.Header.Set("Content-Type", "application/json")
    req.Header.Set("X-MERCHANT-ID", MerchantID)
    req.Header.Set("X-VERIFY", xVerify)

    resp, err := client.Do(req)
    if err != nil {
        return nil, err
    }
    defer resp.Body.Close()

    var pr PayResponse
    if err := json.NewDecoder(resp.Body).Decode(&pr); err != nil {
        return nil, err
    }
    return &pr, nil
}
