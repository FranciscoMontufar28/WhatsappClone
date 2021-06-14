object Dependencies {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger_version}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger_version}"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation_version}"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigation_version}"
    const val androidXCore = "androidx.core:core-ktx:${Versions.androidx_core_version}"
    const val androidXAppCompat =
        "androidx.appcompat:appcompat:${Versions.androidx_appcompat_version}"
    const val countryCodePicker = "com.hbb20:ccp:${Versions.country_code_picker_version}"
    const val circleImageView = "de.hdodenhof:circleimageview:${Versions.circle_image_view_version}"
    const val pixImagePicker = "com.fxn769:pix:${Versions.pix_image_picker_version}"
    const val badgedTableLayout =
        "com.github.rahimlis:badgedtablayout:${Versions.badged_table_layout_version}"
    const val materialSearchBar =
        "com.github.mancj:MaterialSearchBar:${Versions.material_search_bar}"
    const val materialDesign = "com.google.android.material:material:${Versions.material_version}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout_version}"
    const val shapeOfView = "io.github.florent37:shapeofview:${Versions.shape_of_view}"
    const val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.legacy_support_version}"
    const val compressor = "id.zelory:compressor:${Versions.compressor_version}"
    const val fireBasePlatform =
        "com.google.firebase:firebase-bom:${Versions.firebase_platform_version}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseFirestore = "com.google.firebase:firebase-firestore-ktx"
    const val firebaseAuth = "com.google.firebase:firebase-auth-ktx"
    const val firebaseCore = "com.google.firebase:firebase-core:${Versions.firebase_core_version}"
    const val firebaseStorage = "com.google.firebase:firebase-storage-ktx"
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines_version}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_version}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide_version}"
}

object Modules {
    const val data = ":data"
    const val domain = ":domain"
    const val firebaseManager = ":framework:firebasemanager"
    const val sharedPreferences = ":framework:sharedpreferences"
    const val imageManager = ":framework:imagemanager"
}