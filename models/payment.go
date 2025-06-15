package models

import (
	"time"
)

type Payment struct {
	TransactionID string    `gorm:"column:transaction_id;primaryKey;type:uuid" json:"transaction_id"`
	Amount        int64     `gorm:"column:amount" json:"amount"`
	CreatedAt     time.Time `gorm:"column:created_at" json:"created_at"`
	Response      string    `gorm:"column:response" json:"response,omitempty"`
	Status        string    `gorm:"column:status" json:"status,omitempty"`
	UserName      string    `gorm:"column:user_name" json:"user_name,omitempty"`
}

// TableName overrides the default table name used by GORM
func (Payment) TableName() string {
	return "payment"
}
