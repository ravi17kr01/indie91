package routes

import (
	"supabase-fiber-app/controllers"

	"github.com/gofiber/fiber/v2"
)

func RegisterTagRoutes(app fiber.Router) {
	r := app.Group("/api/tags")
	
	r.Post("/", controllers.CreateTag)
	r.Get("/", controllers.GetTags)
	r.Get("/:id", controllers.GetTagByID)
	r.Delete("/:id", controllers.DeleteTag)
}
