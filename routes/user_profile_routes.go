package routes

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/controllers"
)

func RegisterUserProfileRoutes(router fiber.Router) {
	r := router.Group("/api/userProfile")
	
	r.Get("/", controllers.GetAllUserProfiles)
	r.Get("/:userId", controllers.GetSingleUserProfile)
}
