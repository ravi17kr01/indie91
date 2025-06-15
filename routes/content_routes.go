package routes

import (
	"supabase-fiber-app/controllers"

	"github.com/gofiber/fiber/v2"
)

func RegisterContentRoutes(app *fiber.App) {
	route := app.Group("/api/content")

	route.Post("/", controllers.CreateContent)
	route.Get("/", controllers.GetAllContent)
	route.Get("/:id", controllers.GetContentByID)
	route.Put("/:id", controllers.UpdateContent)
	route.Delete("/:id", controllers.DeleteContent)
	route.Put("/:id/like", controllers.UpdateContentLikeCount) // ✅ Like update route
}
