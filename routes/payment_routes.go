package routes

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/controllers"
)

func RegisterPaymentRoutes(router fiber.Router) {
	payment := router.Group("/api")

	payment.Post("/initiate-payment", controllers.InitiatePayment)
    payment.Post("/payment-callback", controllers.PaymentCallback)
}
