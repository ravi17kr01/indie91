package controllers

import (
	"github.com/gofiber/fiber/v2"
	"github.com/google/uuid"
	"supabase-fiber-app/config"
	"supabase-fiber-app/models"
	"time"
)

// CreateUserAddress handles POST /api/addresses/
// Step-by-step: Create a new address for a user
func CreateUserAddress(c *fiber.Ctx) error {
	var address models.UserAddress

	// Step 1: Parse the request body into address struct
	if err := c.BodyParser(&address); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid payload"})
	}

	// Step 2: Generate a new UUID for the address
	address.ID = uuid.New().String()

	// Step 3: Set created and updated timestamps
	address.CreatedAt = time.Now()
	address.UpdatedAt = time.Now()

	// Step 4: Insert the new address into the database
	if err := config.DB.Create(&address).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to create address"})
	}

	// Step 5: Return the created address as a response
	return c.JSON(address)
}

// GetUserAddresses handles GET /api/addresses/:userId
// Step-by-step: Get all addresses for a given user
func GetUserAddresses(c *fiber.Ctx) error {
	// Step 1: Extract user ID from the route parameter
	userID := c.Params("userId")

	// Step 2: Initialize a slice to hold the addresses
	var addresses []models.UserAddress

	// Step 3: Query all addresses where user_id matches
	if err := config.DB.Where("user_id = ?", userID).Find(&addresses).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch addresses"})
	}

	// Step 4: Return the list of addresses
	return c.JSON(addresses)
}

// UpdateUserAddress handles PUT /api/addresses/:id
// Step-by-step: Update a specific address by its ID
func UpdateUserAddress(c *fiber.Ctx) error {
	// Step 1: Get address ID from the URL path
	addressID := c.Params("id")

	// Step 2: Fetch the existing address by ID
	var address models.UserAddress
	if err := config.DB.First(&address, "id = ?", addressID).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Address not found"})
	}

	// Step 3: Parse the request body into updateData struct
	var updateData models.UserAddress
	if err := c.BodyParser(&updateData); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid update payload"})
	}

	// Step 4: Update the timestamp
	updateData.UpdatedAt = time.Now()

	// Step 5: Perform the update
	if err := config.DB.Model(&address).Updates(updateData).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to update address"})
	}

	// Step 6: Return the updated address
	return c.JSON(address)
}

// DeleteUserAddress handles DELETE /api/addresses/:id
// Step-by-step: Delete an address by its ID
func DeleteUserAddress(c *fiber.Ctx) error {
	// Step 1: Extract address ID from the route parameter
	addressID := c.Params("id")

	// Step 2: Delete the address from the database
	if err := config.DB.Delete(&models.UserAddress{}, "id = ?", addressID).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to delete address"})
	}

	// Step 3: Return success message
	return c.JSON(fiber.Map{"message": "Address deleted successfully"})
}
