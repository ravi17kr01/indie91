package controllers

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/config"
	"supabase-fiber-app/models"
)

func CreateProductMedia(c *fiber.Ctx) error {
	var media models.ProductMedia
	if err := c.BodyParser(&media); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}
	if err := config.DB.Create(&media).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to create product media"})
	}
	return c.JSON(media)
}

func GetAllProductMedia(c *fiber.Ctx) error {
	var media []models.ProductMedia
	if err := config.DB.Find(&media).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch product media"})
	}
	return c.JSON(media)
}

func GetProductMediaByID(c *fiber.Ctx) error {
	id := c.Params("id")
	var media models.ProductMedia
	if err := config.DB.First(&media, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Product media not found"})
	}
	return c.JSON(media)
}

func UpdateProductMedia(c *fiber.Ctx) error {
	id := c.Params("id")
	var media models.ProductMedia
	if err := config.DB.First(&media, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Product media not found"})
	}
	var updated models.ProductMedia
	if err := c.BodyParser(&updated); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}
	if err := config.DB.Model(&media).Updates(updated).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to update product media"})
	}
	return c.JSON(media)
}

func DeleteProductMedia(c *fiber.Ctx) error {
	id := c.Params("id")
	if err := config.DB.Delete(&models.ProductMedia{}, "id = ?", id).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to delete product media"})
	}
	return c.JSON(fiber.Map{"message": "Product media deleted"})
}
