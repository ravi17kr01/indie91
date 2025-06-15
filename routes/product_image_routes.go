package routes

import (
	"supabase-fiber-app/controllers"

	"github.com/gofiber/fiber/v2"
)

func RegisterProductImageRoutes(app fiber.Router) {
	productImage := app.Group("/api/product-images")

	productImage.Post("/", controllers.CreateProductImage)
	productImage.Get("/", controllers.GetAllProductImages)
	productImage.Get("/:id", controllers.GetProductImageByID)
	productImage.Put("/:id", controllers.UpdateProductImage)
	productImage.Delete("/:id", controllers.DeleteProductImage)
}
