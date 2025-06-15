// routes/user_address_routes.go
package routes

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/controllers"
)

func RegisterUserAddressRoutes(router fiber.Router) {
	userAddress := router.Group("/addresses")

	userAddress.Post("/", controllers.CreateUserAddress)            // Create address
	userAddress.Get("/:userId", controllers.GetUserAddresses)       // Get all addresses of a user
	userAddress.Put("/:id", controllers.UpdateUserAddress)          // Update address by ID
	userAddress.Delete("/:id", controllers.DeleteUserAddress)       // Delete address by ID
}
