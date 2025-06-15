package routes

import (
	"supabase-fiber-app/controllers"

	"github.com/gofiber/fiber/v2"
)

func RegisterContentProductRoutes(app *fiber.App) {
	route := app.Group("/api/content-products")

	route.Post("/", controllers.CreateContentProduct)
	route.Get("/", controllers.GetAllContentProducts)
	route.Get("/:id", controllers.GetContentProductByID)
	route.Delete("/:id", controllers.DeleteContentProduct)

	// Optional: get all products linked to a specific content
	route.Get("/by-content/:content_id", controllers.GetProductsByContentID)
}
