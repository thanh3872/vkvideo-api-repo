
import com.lagradost.cloudstream3.gradle.CloudstreamExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.library") version "8.0.0" apply false
    kotlin("android") version "1.8.20" apply false
    id("com.lagradost.cloudstream3.gradle") version "1.0.0"
}

cloudstream {
    // Cấu hình Metadata cho Plugin
    setDisplayName("VKVideoAP1I")
    setDescription("Xem video từ VK @jav2026")
    setAuthor("thanh3872")
    setVersion(1)
    setTypes(listOf(com.lagradost.cloudstream3.TvType.Movie))
}

dependencies {
    // Các thư viện hỗ trợ scraping đã nhắc trong tài liệu [cite: 241, 261, 272]
    implementation("org.jsoup:jsoup:1.15.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
