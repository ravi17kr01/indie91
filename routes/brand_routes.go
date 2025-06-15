package routes

import (
	"supabase-fiber-app/controllers"

	"github.com/gofiber/fiber/v2"
)

func RegisterBrandRoutes(app *fiber.App) {
	brand := app.Group("/api/brands")

	brand.Post("/", controllers.CreateBrand)
	brand.Get("/", controllers.GetAllBrands)
	brand.Get("/:id", controllers.GetBrandByID)
	brand.Put("/:id", controllers.UpdateBrand)
	brand.Delete("/:id", controllers.DeleteBrand)
	brand.Put("/:id/like", controllers.UpdateLikeCount) // ✅ New route
}
