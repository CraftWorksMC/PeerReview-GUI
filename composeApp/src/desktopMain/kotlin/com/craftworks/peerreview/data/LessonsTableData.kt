@file:Suppress("PropertyName")

package com.craftworks.peerreview.data

import kotlinx.serialization.Serializable

@Serializable
data class StudentLessonsTableData(
    val id: Int,
    val title: String,
    val created_at: String,
    val first_deadline: String,
    val second_deadline: String,
    val count_questions_made: Int,
    val count_feedback_made: Int,
)

@Serializable
data class TeacherLessonTableData(
    val id: String,
    val title: String,
    val created_at: String,
    val first_deadline: String,
    val second_deadline: String,
    val count_questions: String,
    val count_questions_made: String,
    val answered_questions_range: String,
    val count_feedback_made_range: String,
)

//@Serializable
//data class PeerReviewLessonJsonData(
//    val title: String,
//    val created_at: Date,
//    val first_deadline: Date,
//    val second_deadline: Date,
//    val content_html: String,
//    val course_class_id: Int,
//    val website: Int,
//    val token: String,
//    val role: PeerReviewRole
//)
//
//@Serializable
//data class PeerReviewLessonData(
//    val id: Int,
//    val title: String,
//    val created_at: Date,
//    val first_deadline: Date?,
//    val second_deadline: Date?,
//    val content_html: String,
//    val class_id: Int,
//    val lesson_questions: List<PeerReviewQuestionData>,
//    val reservations_list_json: String,
//)

//@Serializable
//data class PeerReviewSummaryLessonStudentData(
//    val id: Int,
//    val title: String,
//    val created_at: String,
//    val first_deadline: String?,
//    val second_deadline: String?,
//    val count_questions_made: Int,
//    val count_feedback_made: Int
//): PeerReviewData()
//
//@Serializable
//data class PeerReviewSummaryLessonTeacherData(
//    val id: Int,
//    val title: String,
//    val created_at: String?,
//    val first_deadline: String?,
//    val second_deadline: String?,
//    val count_questions: Int,
//    val count_questions_made: Int,
//    val total_answered_questions: Int,
//    val count_feedback_made: Int
//): PeerReviewData()