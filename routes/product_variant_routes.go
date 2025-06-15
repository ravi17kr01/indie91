// routes/product_variant_routes.go
package routes

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/controllers"
)

func RegisterProductVariantRoutes(app fiber.Router) {
	group := app.Group("/api/variants")

	group.Post("/", controllers.CreateProductVariant)
	group.Get("/product/:productId", controllers.GetProductVariantsByProductID)
	group.Put("/:id", controllers.UpdateProductVariant)
	group.Delete("/:id", controllers.DeleteProductVariant)
}
