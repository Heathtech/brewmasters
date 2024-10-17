package org.heathtech.tilt.webserver.domain.data.beer

import java.util.UUID

interface BeerRepository {
    fun getBeers(): List<Beer>

    fun getBeerById(uuid: UUID): Beer?

    fun storeBeer(beer: Beer): Beer

    fun updateBeer(beer: Beer): Beer

    fun removeBeer(uuid: UUID): Beer
}
