package routes

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/controllers"
)

func RegisterOtpRoutes(router fiber.Router) {
	otp := router.Group("/api/otp")
	
	otp.Post("/send", controllers.SendOTP)
	otp.Post("/verify", controllers.VerifyOTP)
}
