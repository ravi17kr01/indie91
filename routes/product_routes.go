package routes

import (
	"supabase-fiber-app/controllers"

	"github.com/gofiber/fiber/v2"
)

func RegisterProductRoutes(app *fiber.App) {
	product := app.Group("/api/products")
	
	product.Post("/", controllers.CreateProduct)
	product.Get("/", controllers.GetProducts)
	product.Get("/:id", controllers.GetProductByID)
	product.Put("/:id", controllers.UpdateProduct)
	product.Delete("/:id", controllers.DeleteProduct)
	product.Put("/:id/like", controllers.UpdateProductLikeCount) 
}
