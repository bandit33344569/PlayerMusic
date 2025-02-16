package com.bro.playermusic.data.local

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.bro.playermusic.data.LocalTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalAudioDataSource(private val context: Context) {

    suspend fun getLocalAudioTracks(): List<LocalTrack> =
        withContext(Dispatchers.IO) {
            val tracks = mutableListOf<LocalTrack>()

            val collection =
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM_ID
            )

            val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

            val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"

            context.contentResolver.query(
                collection,
                projection,
                selection,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val titleColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val artistColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val title = cursor.getString(titleColumn)
                    val artist = cursor.getString(artistColumn)
                    val albumId = cursor.getLong(albumIdColumn)

                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    val albumArtUri: Uri = ContentUris.withAppendedId(
                        Uri.parse("content://media/external/audio/albumart"),
                        albumId
                    )

                    tracks.add(
                        LocalTrack(
                            id = id,
                            title = title,
                            author = artist,
                            uri = contentUri,
                            albumArtUri = albumArtUri
                        )
                    )
                }
            }
            tracks
        }
}