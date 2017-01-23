package io.catnip.postnotes.models;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;

/**
 * Interface that represents valid calls to the PostNotes service
 *
 * Created by james on 1/23/17.
 */

//  Copyright © 2017 Digital Catnip. All rights reserved.
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

public interface PostNotesService {
    @GET("notes")
    Call<List<Note>> getNotes(@Header("Authorization") String authorization);

    @PUT("notes")
    Call<Note> createNote(@Header("Authorization") String authorization, RequestBody body);
}
