package controllers

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/config"
	"supabase-fiber-app/models"
)

// Create brand
func CreateBrand(c *fiber.Ctx) error {
	var brand models.Brand
	if err := c.BodyParser(&brand); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}
	if err := config.DB.Create(&brand).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to create brand"})
	}
	return c.Status(201).JSON(brand)
}

// Get all brands
func GetAllBrands(c *fiber.Ctx) error {
	var brands []models.Brand
	if err := config.DB.Find(&brands).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch brands"})
	}
	return c.JSON(brands)
}

// Get brand by ID
func GetBrandByID(c *fiber.Ctx) error {
	id := c.Params("id")
	var brand models.Brand
	if err := config.DB.First(&brand, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Brand not found"})
	}
	return c.JSON(brand)
}

// Update brand
func UpdateBrand(c *fiber.Ctx) error {
	id := c.Params("id")
	var brand models.Brand
	if err := config.DB.First(&brand, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Brand not found"})
	}

	var updateData models.Brand
	if err := c.BodyParser(&updateData); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}

	if err := config.DB.Model(&brand).Updates(updateData).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to update brand"})
	}
	return c.JSON(brand)
}

// Delete brand
func DeleteBrand(c *fiber.Ctx) error {
	id := c.Params("id")
	if err := config.DB.Delete(&models.Brand{}, "id = ?", id).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to delete brand"})
	}
	return c.JSON(fiber.Map{"message": "Brand deleted successfully"})
}

// UpdateLikeCount updates the like count based on flag ("increase" or "decrease")
func UpdateLikeCount(c *fiber.Ctx) error {
	id := c.Params("id")
	flag := c.Query("flag") // expecting "increase" or "decrease"

	if flag != "increase" && flag != "decrease" {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid flag, use 'increase' or 'decrease'"})
	}

	var brand models.Brand
	if err := config.DB.First(&brand, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Brand not found"})
	}

	if brand.LikeCount == nil {
		initial := 0
		brand.LikeCount = &initial
	}

	if flag == "increase" {
		*brand.LikeCount += 1
	} else if flag == "decrease" && *brand.LikeCount > 0 {
		*brand.LikeCount -= 1
	}

	if err := config.DB.Model(&brand).Update("like_count", *brand.LikeCount).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to update like count"})
	}

	return c.JSON(fiber.Map{
		"message":    "Like count updated successfully",
		"like_count": *brand.LikeCount,
	})
}
