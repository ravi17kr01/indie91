package controllers

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/config"
	"supabase-fiber-app/models"
)

// Create Content
func CreateContent(c *fiber.Ctx) error {
	var content models.Content
	if err := c.BodyParser(&content); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid request body"})
	}
	if err := config.DB.Create(&content).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to create content"})
	}
	return c.JSON(content)
}

// Get All Content
func GetAllContent(c *fiber.Ctx) error {
	var contents []models.Content
	if err := config.DB.Find(&contents).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch contents"})
	}
	return c.JSON(contents)
}

// Get Content by ID
func GetContentByID(c *fiber.Ctx) error {
	id := c.Params("id")
	var content models.Content
	if err := config.DB.First(&content, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Content not found"})
	}
	return c.JSON(content)
}

// Update Content
func UpdateContent(c *fiber.Ctx) error {
	id := c.Params("id")
	var content models.Content
	if err := config.DB.First(&content, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Content not found"})
	}
	if err := c.BodyParser(&content); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid request body"})
	}
	if err := config.DB.Save(&content).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to update content"})
	}
	return c.JSON(content)
}

// Delete Content
func DeleteContent(c *fiber.Ctx) error {
	id := c.Params("id")
	if err := config.DB.Delete(&models.Content{}, "id = ?", id).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to delete content"})
	}
	return c.JSON(fiber.Map{"message": "Content deleted successfully"})
}

// UpdateContentLikeCount updates the likes count of content based on the flag ("increase" or "decrease")
func UpdateContentLikeCount(c *fiber.Ctx) error {
	id := c.Params("id")
	flag := c.Query("flag") // "increase" or "decrease"

	if flag != "increase" && flag != "decrease" {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid flag, must be 'increase' or 'decrease'"})
	}

	var content models.Content
	if err := config.DB.First(&content, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Content not found"})
	}

	if flag == "increase" {
		content.LikesCount += 1
	} else if flag == "decrease" && content.LikesCount > 0 {
		content.LikesCount -= 1
	}

	if err := config.DB.Model(&content).Update("likes_count", content.LikesCount).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to update likes count"})
	}

	return c.JSON(fiber.Map{
		"message":      "Likes count updated successfully",
		"likes_count":  content.LikesCount,
	})
}