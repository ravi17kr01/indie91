package routes

import (
	"supabase-fiber-app/controllers"

	"github.com/gofiber/fiber/v2"
)

func RegisterInfluencerRoutes(app *fiber.App) {
	r := app.Group("/api/influencer-profiles")

	r.Post("/", controllers.CreateInfluencerProfile)
	r.Get("/", controllers.GetAllInfluencerProfiles)
	r.Get("/:id", controllers.GetInfluencerByID)
	r.Get("/by-user/:user_id", controllers.GetInfluencerByUserID)
	r.Put("/:id", controllers.UpdateInfluencer)
	r.Delete("/:id", controllers.DeleteInfluencer)
}
