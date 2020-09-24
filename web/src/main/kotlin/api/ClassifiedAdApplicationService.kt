package marketplace.web.api

import marketplace.domain.ClassifiedAd
import marketplace.domain.ClassifiedAdId
import marketplace.domain.ClassifiedAdText
import marketplace.domain.ClassifiedAdTitle
import marketplace.domain.CurrencyLookup
import marketplace.domain.Price
import marketplace.domain.UserId
import marketplace.framework.ApplicationService
import marketplace.framework.EntityStore
import marketplace.web.contracts.ClassifiedAds.*
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.util.UUID

@Component
class ClassifiedAdApplicationService(private val store: EntityStore, private val currencyLookup: CurrencyLookup)
    : ApplicationService {
    override fun handle(command: Any) {
        when (command) {
            is V1.Create -> handleCreate(command)

            is V1.SetTitle -> handleUpdate(command.id) {
                c -> c.setTitle(ClassifiedAdTitle.fromString(command.title))
            }

            is V1.UpdateText -> handleUpdate(command.id) {
                c -> c.updateText(ClassifiedAdText.fromString(command.text))
            }

            is V1.UpdatePrice -> handleUpdate(command.id) {
                c -> c.updatePrice(Price.fromDouble(command.price, command.currency, currencyLookup))
            }

            is V1.RequestToPublish -> handleUpdate(command.id, ClassifiedAd::requestToPublish)
        }
    }

    private fun handleCreate(command: V1.Create) {
        if (store.exists<ClassifiedAd>(command.id.toString())) {
            throw IllegalStateException("Entity with id ${command.id} already exists")
        }

        val classifiedAd = ClassifiedAd(ClassifiedAdId(command.id), UserId(command.ownerId))

        store.save(classifiedAd)
    }

    private fun handleUpdate(classifiedAdId: UUID, operation: (ClassifiedAd) -> Unit) {
        val classifiedAd = store.load<ClassifiedAd>(classifiedAdId.toString())
            ?: throw IllegalArgumentException("Entity with id $classifiedAdId cannot be found")

        operation(classifiedAd)

        store.save(classifiedAd)
    }
}
