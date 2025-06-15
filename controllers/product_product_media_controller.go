package controllers

import (
	"github.com/gofiber/fiber/v2"
	"supabase-fiber-app/config"
	"supabase-fiber-app/models"
)

func LinkProductMedia(c *fiber.Ctx) error {
	var link models.ProductProductMedia
	if err := c.BodyParser(&link); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}
	if err := config.DB.Create(&link).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to link product and media"})
	}
	return c.JSON(link)
}

func GetAllProductMediaLinks(c *fiber.Ctx) error {
	var links []models.ProductProductMedia
	if err := config.DB.Find(&links).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch links"})
	}
	return c.JSON(links)
}

func DeleteProductMediaLink(c *fiber.Ctx) error {
	productID := c.Query("product_id")
	mediaID := c.Query("media_id")

	if productID == "" || mediaID == "" {
		return c.Status(400).JSON(fiber.Map{"error": "Missing product_id or media_id"})
	}

	if err := config.DB.Where("product_id = ? AND product_media_id = ?", productID, mediaID).
		Delete(&models.ProductProductMedia{}).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to delete link"})
	}
	return c.JSON(fiber.Map{"message": "Link deleted"})
}

func GetMediaByProductID(c *fiber.Ctx) error {
	productID := c.Params("product_id")
	if productID == "" {
		return c.Status(400).JSON(fiber.Map{"error": "Missing product_id"})
	}

	var links []models.ProductProductMedia
	if err := config.DB.Preload("ProductMedia").
		Where("product_id = ?", productID).Find(&links).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch media for product"})
	}

	var media []models.ProductMedia
	for _, link := range links {
		media = append(media, link.ProductMedia)
	}

	return c.JSON(media)
}
