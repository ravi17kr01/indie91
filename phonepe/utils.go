package phonepe

import (
    "crypto/sha256"
    "encoding/base64"
    "fmt"
)

func EncodeRequest(body []byte) string {
    return base64.StdEncoding.EncodeToString(body)
}

func GenerateXVerify(encodedPayload string) string {
    // SHA256(Base64(payload) + endpoint + saltKey) + ### + saltIndex
    data := encodedPayload + PayEndpoint + SaltKey
    hash := sha256.Sum256([]byte(data))
    return fmt.Sprintf("%x###%s", hash, SaltIndex)
}
