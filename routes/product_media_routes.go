package routes

import (
	"supabase-fiber-app/controllers"

	"github.com/gofiber/fiber/v2"
)

func RegisterProductMediaRoutes(app fiber.Router) {
	r := app.Group("/api/product-media")

	r.Post("/", controllers.CreateProductMedia)
	r.Get("/", controllers.GetAllProductMedia)
	r.Get("/:id", controllers.GetProductMediaByID)
	r.Put("/:id", controllers.UpdateProductMedia)
	r.Delete("/:id", controllers.DeleteProductMedia)
}
