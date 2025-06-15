package routes

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/controllers"
)

func RegisterOrderRoutes(router fiber.Router) {
	order := router.Group("/api/orders")
	
	order.Get("/", controllers.GetOrders)
	order.Get("/:id", controllers.GetOrderByID)
	order.Get("/user/:userId", controllers.GetOrdersByUserID)
	order.Post("/", controllers.CreateOrder)
	order.Put("/:id", controllers.UpdateOrder)
	order.Delete("/:id", controllers.DeleteOrder)
}
