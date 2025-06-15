package controllers

import (
	"supabase-fiber-app/services"
	"github.com/gofiber/fiber/v2"
)

// GET / - fetch all user profiles
func GetAllUserProfiles(c *fiber.Ctx) error {
	result, err := services.GetAllUserProfiles()
	if err != nil {
		return c.Status(fiber.StatusInternalServerError).JSON(fiber.Map{
			"error": err.Error(),
		})
	}
	return c.JSON(result)
}

// GET /:userId - fetch single user profile by user ID
func GetSingleUserProfile(c *fiber.Ctx) error {
	userId := c.Params("userId")

	result, err := services.GetSingleUserProfile(userId)
	if err != nil {
		return c.Status(fiber.StatusInternalServerError).JSON(fiber.Map{
			"error": err.Error(),
		})
	}
	return c.JSON(result)
}
