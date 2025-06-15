package controllers

import (
	"log"
	"supabase-fiber-app/config"
	"supabase-fiber-app/models"
	"time"

	"github.com/gofiber/fiber/v2"
	"github.com/google/uuid"
	"gorm.io/gorm"
)

func GetOrders(c *fiber.Ctx) error {
	var orders []models.Order
	if err := config.DB.Preload("CartItems").Preload("AppUser").Find(&orders).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch orders"})
	}
	return c.JSON(orders)
}

func GetOrderByID(c *fiber.Ctx) error {
	id := c.Params("id")
	var order models.Order
	if err := config.DB.Preload("CartItems").Preload("AppUser").First(&order, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Order not found"})
	}
	return c.JSON(order)
}

func GetOrdersByUserID(c *fiber.Ctx) error {
	userID := c.Params("userId")
	var orders []models.Order
	if err := config.DB.Preload("CartItems").Preload("AppUser").Where("user_id = ?", userID).Find(&orders).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to fetch orders for user"})
	}
	return c.JSON(orders)
}

// func CreateOrder(c *fiber.Ctx) error {
// 	var order models.Order
// 	if err := c.BodyParser(&order); err != nil {
// 		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
// 	}
// 	if err := config.DB.Create(&order).Error; err != nil {
// 		return c.Status(500).JSON(fiber.Map{"error": "Failed to create order"})
// 	}
// 	return c.JSON(order)
// }

func CreateOrder(c *fiber.Ctx) error {
	var inputOrder models.Order

	// Parse request
	if err := c.BodyParser(&inputOrder); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}
	if inputOrder.UserID == nil {
		return c.Status(400).JSON(fiber.Map{"error": "user_id is required"})
	}

	// Set default values
	if inputOrder.ID == "" {
		inputOrder.ID = uuid.New().String()
	}
	if inputOrder.Date == nil {
		now := time.Now()
		inputOrder.Date = &now
	}

	// Transaction
	if err := config.DB.Transaction(func(tx *gorm.DB) error {
		var existingOrder models.Order

		// 1. Check if order already exists for user
		err := tx.Where("user_id = ?", *inputOrder.UserID).First(&existingOrder).Error
		if err != nil && err != gorm.ErrRecordNotFound {
			return err
		}

		// 2. If exists, update. Else, insert.
		if err == nil {
			inputOrder.ID = existingOrder.ID // use existing ID
			if err := tx.Model(&existingOrder).Updates(map[string]interface{}{
				"address":      inputOrder.Address,
				"date":         inputOrder.Date,
				"description":  inputOrder.Description,
				"status":       inputOrder.Status,
				"total_amount": inputOrder.TotalAmount,
				"tracking_id":  inputOrder.TrackingID,
			}).Error; err != nil {
				log.Println("Failed to update existing order:", err)
				return err
			}
		} else {
			if err := tx.Create(&inputOrder).Error; err != nil {
				log.Println("Failed to insert new order:", err)
				return err
			}
		}

		// 3. Delete old cart items linked to the order ID
		if err := tx.Where("order_id = ?", inputOrder.ID).Delete(&models.CartItem{}).Error; err != nil {
			log.Println("Failed to delete existing cart items:", err)
			return err
		}

		// 4. Insert new cart items
		for i := range inputOrder.CartItems {
			inputOrder.CartItems[i].ID = uuid.New().String()
			inputOrder.CartItems[i].OrderID = inputOrder.ID
			inputOrder.CartItems[i].UserID = *inputOrder.UserID
		}

		if len(inputOrder.CartItems) > 0 {
			if err := tx.Create(&inputOrder.CartItems).Error; err != nil {
				log.Println("Failed to insert cart items:", err)
				return err
			}
		}

		return nil
	}); err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to create or update order"})
	}

	return c.JSON(inputOrder)
}


func UpdateOrder(c *fiber.Ctx) error {
	id := c.Params("id")
	var order models.Order
	if err := config.DB.First(&order, "id = ?", id).Error; err != nil {
		return c.Status(404).JSON(fiber.Map{"error": "Order not found"})
	}
	var updated models.Order
	if err := c.BodyParser(&updated); err != nil {
		return c.Status(400).JSON(fiber.Map{"error": "Invalid input"})
	}
	if err := config.DB.Model(&order).Updates(updated).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to update order"})
	}
	return c.JSON(order)
}

func DeleteOrder(c *fiber.Ctx) error {
	id := c.Params("id")
	if err := config.DB.Delete(&models.Order{}, "id = ?", id).Error; err != nil {
		return c.Status(500).JSON(fiber.Map{"error": "Failed to delete order"})
	}
	return c.JSON(fiber.Map{"message": "Order deleted"})
}
