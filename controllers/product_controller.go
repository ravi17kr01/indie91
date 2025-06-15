package controllers

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/config"
	"supabase-fiber-app/models"
)

func CreateProduct(c *fiber.Ctx) error {
	var product models.Product
	if err := c.BodyParser(&product); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}
	if err := config.DB.Create(&product).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to create product"})
	}
	return c.JSON(product)
}

func GetProducts(c *fiber.Ctx) error {
	var products []models.Product
	if err := config.DB.Find(&products).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch products"})
	}
	return c.JSON(products)
}

func GetProductByID(c *fiber.Ctx) error {
	id := c.Params("id")
	var product models.Product
	if err := config.DB.First(&product, "product_id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Product not found"})
	}
	return c.JSON(product)
}

func UpdateProduct(c *fiber.Ctx) error {
	id := c.Params("id")
	var product models.Product
	if err := config.DB.First(&product, "product_id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Product not found"})
	}
	var updated models.Product
	if err := c.BodyParser(&updated); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}
	config.DB.Model(&product).Updates(updated)
	return c.JSON(product)
}

func DeleteProduct(c *fiber.Ctx) error {
	id := c.Params("id")
	if err := config.DB.Delete(&models.Product{}, "product_id = ?", id).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to delete"})
	}
	return c.JSON(fiber.Map{"message": "Product deleted"})
}

// UpdateProductLikeCount updates like count based on the flag ("increase" or "decrease")
func UpdateProductLikeCount(c *fiber.Ctx) error {
	id := c.Params("id")
	flag := c.Query("flag") // expecting "increase" or "decrease"

	if flag != "increase" && flag != "decrease" {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid flag, use 'increase' or 'decrease'"})
	}

	var product models.Product
	if err := config.DB.First(&product, "product_id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Product not found"})
	}

	if product.LikeCount == nil {
		initial := 0
		product.LikeCount = &initial
	}

	if flag == "increase" {
		*product.LikeCount += 1
	} else if flag == "decrease" && *product.LikeCount > 0 {
		*product.LikeCount -= 1
	}

	if err := config.DB.Model(&product).Update("like_count", *product.LikeCount).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to update like count"})
	}

	return c.JSON(fiber.Map{
		"message":    "Like count updated successfully",
		"like_count": *product.LikeCount,
	})
}