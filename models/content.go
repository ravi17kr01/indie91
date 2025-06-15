package models

import "time"

type Content struct {
	ID                string    `gorm:"type:uuid;default:gen_random_uuid();primaryKey" json:"id"`
	InfluencerID      string    `gorm:"type:uuid" json:"influencer_id"`
	ContentType       string    `gorm:"type:varchar(5);default:IMAGE" json:"content_type"`
	URL               string    `json:"url"`
	CreatedAt         time.Time `gorm:"default:now()" json:"created_at"`
	UpdatedAt         time.Time `gorm:"default:now()" json:"updated_at"`
	IsActive          bool      `gorm:"default:true" json:"is_active"`
	ViewsCount        int       `gorm:"default:0" json:"views_count"`
	LikesCount        int       `gorm:"default:0" json:"likes_count"`
	TagID             *string   `gorm:"type:uuid" json:"tag_id"`
	Description       string    `json:"description"`
	ShareCount        int       `json:"share_count"`
}
