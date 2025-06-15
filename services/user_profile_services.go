package services

import (
	"encoding/json"
	"supabase-fiber-app/config"
)

func GetAllUserProfiles() ([]map[string]interface{}, error) {
	query := `SELECT jsonb_agg(user_data) AS result
	FROM (
		SELECT
			u.id AS userid,
			u.name AS username,
			u.email,
			u.phone,
			u.avatar_url AS avatarurl,
			ip.id AS influencerprofileid,
			ip.total_earnings AS totalearnings,
			ip.current_earnings AS currentearnings,
			ip.total_sales AS totalsales,
			ip.like_count AS likeCount,
			ip.engagement_rate AS engagementrate,
			ip.brand_collaborations AS brandcollaborations,
			(
				SELECT jsonb_agg(
					jsonb_build_object(
						'contentId', c.id,
						'contentType', c.content_type,
						'contentUrl', c.url,
						'contentViewsCount', c.views_count,
						'contentLikesCount', c.likes_count,
						'contentShareCount', c.share_count,
						'contentDescription', c.description,
						'tag', jsonb_build_object(
							'tagId', t.id,
							'tagName', t.name
						),
						'products', (
							SELECT jsonb_agg(
								jsonb_build_object(
									'productId', p.product_id,
									'productName', p.name,
									'productViewsCount', p.view_count,
									'productLikesCount', p.like_count,
									'productShareCount', p.share_count,
									'caption', p.caption,
									'price', p.price,
									'brandId', b.id,
									'brandName', b.name,
									'brandLogo', b.logo_url,
									'brandViewsCount', b.view_count,
									'brandLikesCount', b.like_count,
									'brandShareCount', b.share_count,
									'productImages', (
										SELECT jsonb_agg(
											jsonb_build_object(
												'imageUrl', pi.image_url,
												'altText', pi.alt_text
											)
										) FROM product_image pi
										WHERE pi.product_id = p.product_id
									),
									'productMedia', (
										SELECT jsonb_agg(
											jsonb_build_object(
												'mediaId', pm.id,
												'mediaUrl', pm.url
											)
										) FROM product_product_media ppm
										JOIN product_media pm ON ppm.product_media_id = pm.id
										WHERE ppm.product_id = p.product_id
									)
								)
							) FROM content_products cp
							JOIN product p ON p.product_id = cp.product_id
							LEFT JOIN brand b ON b.id = p.brand_id
							WHERE cp.content_id = c.id
						)
					)
				) FROM content c
				LEFT JOIN tag t ON t.id = c.tag_id
				WHERE c.influencer_id = ip.id
			) AS contents
		FROM app_user u
		LEFT JOIN influencer_profile ip ON u.id = ip.user_id
	) AS user_data;`
	
	var result []byte
	row := config.DB.Raw(query).Row()
	if err := row.Scan(&result); err != nil {
		return nil, err
	}

	var parsed []map[string]interface{}
	if err := json.Unmarshal(result, &parsed); err != nil {
		return nil, err
	}

	return parsed, nil
}

func GetSingleUserProfile(userId string) ([]map[string]interface{}, error) {
	query := `SELECT jsonb_agg(user_data) AS result
	FROM (
		SELECT
			u.id AS userid,
			u.name AS username,
			u.email,
			u.phone,
			u.avatar_url AS avatarurl,
			ip.id AS influencerprofileid,
			ip.total_earnings AS totalearnings,
			ip.current_earnings AS currentearnings,
			ip.total_sales AS totalsales,
			ip.like_count AS likeCount,
			ip.engagement_rate AS engagementrate,
			ip.brand_collaborations AS brandcollaborations,
			(
				SELECT jsonb_agg(
					jsonb_build_object(
						'contentId', c.id,
						'contentType', c.content_type,
						'contentUrl', c.url,
						'contentViewsCount', c.views_count,
						'contentLikesCount', c.likes_count,
						'contentShareCount', c.share_count,
						'contentDescription', c.description,
						'tag', jsonb_build_object(
							'tagId', t.id,
							'tagName', t.name
						),
						'products', (
							SELECT jsonb_agg(
								jsonb_build_object(
									'productId', p.product_id,
									'productName', p.name,
									'productViewsCount', p.view_count,
									'productLikesCount', p.like_count,
									'productShareCount', p.share_count,
									'caption', p.caption,
									'price', p.price,
									'brandId', b.id,
									'brandName', b.name,
									'brandLogo', b.logo_url,
									'brandViewsCount', b.view_count,
									'brandLikesCount', b.like_count,
									'brandShareCount', b.share_count,
									'productImages', (
										SELECT jsonb_agg(
											jsonb_build_object(
												'imageUrl', pi.image_url,
												'altText', pi.alt_text
											)
										) FROM product_image pi
										WHERE pi.product_id = p.product_id
									),
									'productMedia', (
										SELECT jsonb_agg(
											jsonb_build_object(
												'mediaId', pm.id,
												'mediaUrl', pm.url
											)
										) FROM product_product_media ppm
										JOIN product_media pm ON ppm.product_media_id = pm.id
										WHERE ppm.product_id = p.product_id
									)
								)
							) FROM content_products cp
							JOIN product p ON p.product_id = cp.product_id
							LEFT JOIN brand b ON b.id = p.brand_id
							WHERE cp.content_id = c.id
						)
					)
				) FROM content c
				LEFT JOIN tag t ON t.id = c.tag_id
				WHERE c.influencer_id = ip.id
			) AS contents
		FROM app_user u
		LEFT JOIN influencer_profile ip ON u.id = ip.user_id
		WHERE u.id = $1
	) AS user_data;`


	var result []byte
	row := config.DB.Raw(query, userId).Row()
	if err := row.Scan(&result); err != nil {
		return nil, err
	}

	var parsed []map[string]interface{}
	if err := json.Unmarshal(result, &parsed); err != nil {
		return nil, err
	}

	return parsed, nil
}
