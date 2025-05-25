package com.example.indie91.Repositories;

import com.example.indie91.DTO.UserFullProfileProjection;
import com.example.indie91.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface UserProfileRepository extends CrudRepository<User, UUID> {
    @Query(value = """
    SELECT jsonb_agg(user_data) AS result
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
            ip.followers_count AS followerscount,
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
    ) AS user_data;
    """, nativeQuery = true)
    String getFullUserProfileJson();

    @Query(value = """
    SELECT jsonb_agg(user_data) AS result
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
            ip.followers_count AS followerscount,
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
        WHERE u.id = :userId
    ) AS user_data;
    """, nativeQuery = true)
    String getSingleUserProfileJson(@Param("userId") UUID userId);
}
