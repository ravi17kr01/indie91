package routes

import (
	"supabase-fiber-app/controllers"

	"github.com/gofiber/fiber/v2"
)

func RegisterProductProductMediaRoutes(app fiber.Router) {
	r := app.Group("/api/product-product-media")

	r.Post("/", controllers.LinkProductMedia)
	r.Get("/", controllers.GetAllProductMediaLinks)
	r.Delete("/", controllers.DeleteProductMediaLink)
	r.Get("/product/:product_id", controllers.GetMediaByProductID)
}
