package controllers

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/config"
	"supabase-fiber-app/models"
)

// Create ContentProduct
func CreateContentProduct(c *fiber.Ctx) error {
	var cp models.ContentProduct
	if err := c.BodyParser(&cp); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid body"})
	}
	if err := config.DB.Create(&cp).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to create content-product link"})
	}
	return c.JSON(cp)
}

// Get All ContentProducts
func GetAllContentProducts(c *fiber.Ctx) error {
	var cps []models.ContentProduct
	if err := config.DB.Find(&cps).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch data"})
	}
	return c.JSON(cps)
}

// Get by ID
func GetContentProductByID(c *fiber.Ctx) error {
	id := c.Params("id")
	var cp models.ContentProduct
	if err := config.DB.First(&cp, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "ContentProduct not found"})
	}
	return c.JSON(cp)
}

// Delete by ID
func DeleteContentProduct(c *fiber.Ctx) error {
	id := c.Params("id")
	if err := config.DB.Delete(&models.ContentProduct{}, "id = ?", id).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to delete"})
	}
	return c.JSON(fiber.Map{"message": "Deleted successfully"})
}

// Get Products by Content ID
func GetProductsByContentID(c *fiber.Ctx) error {
	contentID := c.Params("content_id")
	var cps []models.ContentProduct
	if err := config.DB.Where("content_id = ?", contentID).Find(&cps).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch content's products"})
	}
	return c.JSON(cps)
}
