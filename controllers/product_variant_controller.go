// controllers/product_variant_controller.go
package controllers

import (
	"github.com/gofiber/fiber/v2"
	"github.com/google/uuid"
	"supabase-fiber-app/config"
	"supabase-fiber-app/models"
	"time"
)

// Create a new product variant
func CreateProductVariant(c *fiber.Ctx) error {
	var variant models.ProductVariant

	if err := c.BodyParser(&variant); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid payload"})
	}

	variant.ID = uuid.New().String()
	variant.CreatedAt = time.Now()
	variant.UpdatedAt = time.Now()

	if err := config.DB.Create(&variant).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to create product variant"})
	}

	return c.JSON(variant)
}

// Get all variants for a given product
func GetProductVariantsByProductID(c *fiber.Ctx) error {
	productID := c.Params("productId")
	var variants []models.ProductVariant

	if err := config.DB.Where("product_id = ?", productID).Find(&variants).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch product variants"})
	}

	return c.JSON(variants)
}

// Update a variant by ID
func UpdateProductVariant(c *fiber.Ctx) error {
	id := c.Params("id")
	var existing models.ProductVariant

	if err := config.DB.First(&existing, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Variant not found"})
	}

	var updateData models.ProductVariant
	if err := c.BodyParser(&updateData); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid update payload"})
	}

	updateData.UpdatedAt = time.Now()

	if err := config.DB.Model(&existing).Updates(updateData).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to update variant"})
	}

	return c.JSON(existing)
}

// Delete a variant by ID
func DeleteProductVariant(c *fiber.Ctx) error {
	id := c.Params("id")

	if err := config.DB.Delete(&models.ProductVariant{}, "id = ?", id).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to delete variant"})
	}

	return c.JSON(fiber.Map{"message": "Variant deleted successfully"})
}
