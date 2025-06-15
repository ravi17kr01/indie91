package routes

import (
	"supabase-fiber-app/controllers"

	"github.com/gofiber/fiber/v2"
)

func RegisterCartItemRoutes(app *fiber.App) {
	cart := app.Group("/api/cart-items")

	cart.Post("/", controllers.CreateCartItem)
	cart.Get("/", controllers.GetAllCartItems)
	cart.Get("/user/:user_id", controllers.GetCartItemsByUserID)
	cart.Get("/:id", controllers.GetCartItemByID)
	cart.Put("/:id", controllers.UpdateCartItem)
	cart.Delete("/:id", controllers.DeleteCartItem)
}
