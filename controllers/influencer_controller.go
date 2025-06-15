package controllers

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/config"
	"supabase-fiber-app/models"
)

// Create Influencer Profile
func CreateInfluencerProfile(c *fiber.Ctx) error {
	var influencer models.InfluencerProfile
	if err := c.BodyParser(&influencer); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid body"})
	}
	if err := config.DB.Create(&influencer).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to create"})
	}
	return c.JSON(influencer)
}

// Get All Influencer Profiles
func GetAllInfluencerProfiles(c *fiber.Ctx) error {
	var influencers []models.InfluencerProfile
	if err := config.DB.Find(&influencers).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch"})
	}
	return c.JSON(influencers)
}

// Get By ID
func GetInfluencerByID(c *fiber.Ctx) error {
	id := c.Params("id")
	var influencer models.InfluencerProfile
	if err := config.DB.First(&influencer, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Not found"})
	}
	return c.JSON(influencer)
}

// Get By User ID
func GetInfluencerByUserID(c *fiber.Ctx) error {
	userID := c.Params("user_id")
	var influencer models.InfluencerProfile
	if err := config.DB.First(&influencer, "user_id = ?", userID).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Not found for user"})
	}
	return c.JSON(influencer)
}

// Update
func UpdateInfluencer(c *fiber.Ctx) error {
	id := c.Params("id")
	var data models.InfluencerProfile
	if err := c.BodyParser(&data); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}
	if err := config.DB.Model(&models.InfluencerProfile{}).Where("id = ?", id).Updates(data).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to update"})
	}
	return c.JSON(fiber.Map{"message": "Updated successfully"})
}

// Delete
func DeleteInfluencer(c *fiber.Ctx) error {
	id := c.Params("id")
	if err := config.DB.Delete(&models.InfluencerProfile{}, "id = ?", id).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Delete failed"})
	}
	return c.JSON(fiber.Map{"message": "Deleted successfully"})
}
