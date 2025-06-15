package controllers

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/config"
	"supabase-fiber-app/models"
)

// Create CartItem
func CreateCartItem(c *fiber.Ctx) error {
	var item models.CartItem
	if err := c.BodyParser(&item); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}
	if err := config.DB.Create(&item).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to create cart item"})
	}
	return c.Status(201).JSON(item)
}

// Get All CartItems
func GetAllCartItems(c *fiber.Ctx) error {
	var items []models.CartItem
	if err := config.DB.Find(&items).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch cart items"})
	}
	return c.JSON(items)
}

// Get CartItem by ID
func GetCartItemByID(c *fiber.Ctx) error {
	id := c.Params("id")
	var item models.CartItem
	if err := config.DB.First(&item, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Cart item not found"})
	}
	return c.JSON(item)
}

// Update CartItem
func UpdateCartItem(c *fiber.Ctx) error {
	id := c.Params("id")
	var item models.CartItem
	if err := config.DB.First(&item, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Cart item not found"})
	}

	var updateData models.CartItem
	if err := c.BodyParser(&updateData); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}

	if err := config.DB.Model(&item).Updates(updateData).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to update cart item"})
	}
	return c.JSON(item)
}

// Delete CartItem
func DeleteCartItem(c *fiber.Ctx) error {
	id := c.Params("id")
	if err := config.DB.Delete(&models.CartItem{}, "id = ?", id).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to delete cart item"})
	}
	return c.JSON(fiber.Map{"message": "Cart item deleted successfully"})
}

// Get CartItems by User ID
func GetCartItemsByUserID(c *fiber.Ctx) error {
	userID := c.Params("user_id")
	var items []models.CartItem

	if err := config.DB.
		Where("user_id = ?", userID).
		Find(&items).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch cart items for user"})
	}

	return c.JSON(items)
}
