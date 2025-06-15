package models

import (
	"time"
)

type InfluencerProfile struct {
	ID                   string     `gorm:"type:uuid;default:gen_random_uuid();primaryKey" json:"id"`
	TotalEarnings        float64    `gorm:"type:decimal(38,2);default:0.00" json:"total_earnings"`
	CurrentEarnings      float64    `gorm:"type:decimal(38,2);default:0.00" json:"current_earnings"`
	LastPaidAt           *time.Time `gorm:"type:timestamp(6)" json:"last_paid_at"`
	TotalSales           int        `gorm:"default:0" json:"total_sales"`
	LikeCount            int        `gorm:"default:0" json:"like_count"`
	EngagementRate       float64    `gorm:"default:0.0" json:"engagement_rate"`
	BrandCollaborations  int        `gorm:"default:0" json:"brand_collaborations"`
	CreatedAt            *time.Time `gorm:"default:now()" json:"created_at"`
	UpdatedAt            *time.Time `gorm:"default:now()" json:"updated_at"`
	UserID               *string    `gorm:"type:uuid" json:"user_id"`
	ContentCount         int        `json:"content_count"`
	FollowersCount       int        `json:"followers_count"`

	AppUser AppUser `gorm:"foreignKey:UserID;constraint:OnDelete:CASCADE;" json:"-"`
}

