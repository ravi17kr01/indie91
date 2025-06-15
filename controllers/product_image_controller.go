package controllers

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/config"
	"supabase-fiber-app/models"
)

func CreateProductImage(c *fiber.Ctx) error {
	var image models.ProductImage
	if err := c.BodyParser(&image); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}
	if err := config.DB.Create(&image).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to create product image"})
	}
	return c.JSON(image)
}

func GetAllProductImages(c *fiber.Ctx) error {
	var images []models.ProductImage
	if err := config.DB.Find(&images).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch product images"})
	}
	return c.JSON(images)
}

func GetProductImageByID(c *fiber.Ctx) error {
	id := c.Params("id")
	var image models.ProductImage
	if err := config.DB.First(&image, "image_id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Product image not found"})
	}
	return c.JSON(image)
}

func UpdateProductImage(c *fiber.Ctx) error {
	id := c.Params("id")
	var image models.ProductImage
	if err := config.DB.First(&image, "image_id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Product image not found"})
	}

	var updated models.ProductImage
	if err := c.BodyParser(&updated); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}

	if err := config.DB.Model(&image).Updates(updated).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to update product image"})
	}
	return c.JSON(image)
}

func DeleteProductImage(c *fiber.Ctx) error {
	id := c.Params("id")
	if err := config.DB.Delete(&models.ProductImage{}, "image_id = ?", id).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to delete product image"})
	}
	return c.JSON(fiber.Map{"message": "Product image deleted"})
}
