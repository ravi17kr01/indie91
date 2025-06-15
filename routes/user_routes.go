package routes

import (
	"supabase-fiber-app/controllers"

	"github.com/gofiber/fiber/v2"
)

func RegisterUserRoutes(app *fiber.App) {
	user := app.Group("/api/users")

	user.Post("/", controllers.CreateUser)
	user.Get("/", controllers.GetAllUsers)
	user.Get("/:id", controllers.GetUserByID)
	user.Put("/:id", controllers.UpdateUser)
	user.Delete("/:id", controllers.DeleteUser)
}
