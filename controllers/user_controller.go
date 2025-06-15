package controllers

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/models"
	"supabase-fiber-app/config"
)

// Create
func CreateUser(c *fiber.Ctx) error {
	var user models.AppUser
	if err := c.BodyParser(&user); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}
	if err := config.DB.Create(&user).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Could not create user"})
	}
	return c.Status(201).JSON(user)
}

// Read All
func GetAllUsers(c *fiber.Ctx) error {
	var users []models.AppUser
	if err := config.DB.Find(&users).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch users"})
	}
	return c.JSON(users)
}

// Read by ID
func GetUserByID(c *fiber.Ctx) error {
	id := c.Params("id")
	var user models.AppUser
	if err := config.DB.First(&user, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "User not found"})
	}
	return c.JSON(user)
}

// Update
func UpdateUser(c *fiber.Ctx) error {
	id := c.Params("id")
	var user models.AppUser
	if err := config.DB.First(&user, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "User not found"})
	}

	var updateData models.AppUser
	if err := c.BodyParser(&updateData); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}

	if err := config.DB.Model(&user).Updates(updateData).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to update user"})
	}
	return c.JSON(user)
}

// Delete
func DeleteUser(c *fiber.Ctx) error {
	id := c.Params("id")
	if err := config.DB.Delete(&models.AppUser{}, "id = ?", id).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to delete user"})
	}
	return c.JSON(fiber.Map{"message": "User deleted successfully"})
}
