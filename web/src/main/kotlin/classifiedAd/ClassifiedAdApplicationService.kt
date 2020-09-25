package marketplace.web.classifiedAd

import marketplace.domain.CurrencyLookup
import marketplace.domain.UserId
import marketplace.domain.classifiedAd.ClassifiedAd
import marketplace.domain.classifiedAd.ClassifiedAdId
import marketplace.domain.classifiedAd.ClassifiedAdRepository
import marketplace.domain.classifiedAd.ClassifiedAdText
import marketplace.domain.classifiedAd.ClassifiedAdTitle
import marketplace.domain.classifiedAd.Price
import marketplace.framework.ApplicationService
import marketplace.framework.UnitOfWork
import marketplace.web.classifiedAd.Contracts.V1
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ClassifiedAdApplicationService(
    private val repository: ClassifiedAdRepository,
    private val unitOfWork: UnitOfWork,
    private val currencyLookup: CurrencyLookup
) : ApplicationService {
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
        if (repository.exists(ClassifiedAdId(command.id))) {
            throw IllegalStateException("Entity with id ${command.id} already exists")
        }

        val classifiedAd = ClassifiedAd(ClassifiedAdId(command.id), UserId(command.ownerId))

        repository.add(classifiedAd)
        unitOfWork.commit()
    }

    private fun handleUpdate(classifiedAdId: UUID, operation: (ClassifiedAd) -> Unit) {
        val classifiedAd = repository.load(ClassifiedAdId(classifiedAdId))
            ?: throw IllegalArgumentException("Entity with id $classifiedAdId cannot be found")

        operation(classifiedAd)

        repository.update(classifiedAd)
        unitOfWork.commit()
    }
}
