package controllers

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/config"
	"supabase-fiber-app/models"
)

func CreateTag(c *fiber.Ctx) error {
	var tag models.Tag
	if err := c.BodyParser(&tag); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}
	if err := config.DB.Create(&tag).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to create tag"})
	}
	return c.JSON(tag)
}

func GetTags(c *fiber.Ctx) error {
	var tags []models.Tag
	if err := config.DB.Find(&tags).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch tags"})
	}
	return c.JSON(tags)
}

func GetTagByID(c *fiber.Ctx) error {
	id := c.Params("id")
	var tag models.Tag
	if err := config.DB.First(&tag, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Tag not found"})
	}
	return c.JSON(tag)
}

func DeleteTag(c *fiber.Ctx) error {
	id := c.Params("id")
	if err := config.DB.Delete(&models.Tag{}, "id = ?", id).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to delete tag"})
	}
	return c.JSON(fiber.Map{"message": "Tag deleted"})
}
