package utils

import (
	"crypto/rand"
	"fmt"
	"math/big"
)

// GenerateOTP returns a 6-digit random OTP
func GenerateOTP() (string, error) {
	const digits = "0123456789"
	otp := ""
	for i := 0; i < 6; i++ {
		n, err := rand.Int(rand.Reader, big.NewInt(int64(len(digits))))
		if err != nil {
			return "", err
		}
		otp += string(digits[n.Int64()])
	}
	fmt.Println("OTP>>>>>>>>>>>>>>>>>>", otp)
	return otp, nil
}

// SaveOTP stores OTP in Redis with expiry
// func SaveOTP(phone, otp string) error {
// 	return config.RedisClient.Set(config.Ctx, phone, otp, 5*time.Minute).Err()
// }

// VerifyOTP compares given OTP with stored one
// func VerifyOTP(phone, input string) bool {
// 	val, err := config.RedisClient.Get(config.Ctx, phone).Result()
// 	if err != nil || val != input {
// 		return false
// 	}
// 	config.RedisClient.Del(config.Ctx, phone)
// 	return true
// }
