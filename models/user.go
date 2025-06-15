package models

import (
	"time"
)

type AppUser struct {
	ID             string     `gorm:"type:uuid;default:gen_random_uuid();primaryKey" json:"id"`
	Name           string     `json:"name"`
	Phone          string     `gorm:"unique" json:"phone"`
	Email          *string    `gorm:"unique" json:"email,omitempty"`
	FirebaseUID    *string    `gorm:"unique" json:"firebase_uid,omitempty"`
	IsStaff        bool       `gorm:"default:false" json:"is_staff"`
	IsSuperuser    bool       `gorm:"default:false" json:"is_superuser"`
	IsActive       bool       `gorm:"default:true" json:"is_active"`
	JoinedOn       time.Time  `gorm:"default:now()" json:"joined_on"`
	AvatarURL      *string    `json:"avatar_url,omitempty"`
}

func (AppUser) TableName() string {
	return "app_user"
}
