package be.woutschoovaerts.mollie.handler.payments;

import be.woutschoovaerts.mollie.data.wallet.ApplePaySessionRequest;
import be.woutschoovaerts.mollie.data.wallet.ApplePaySessionResponse;
import be.woutschoovaerts.mollie.exception.MollieException;
import be.woutschoovaerts.mollie.util.RestService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WalletHandler {

    private static final TypeReference<ApplePaySessionResponse> APPLE_PAY_SESSION_RESPONSE_TYPE = new TypeReference<>() {
    };

    private final RestService restService;

    /**
     * For integrating Apple Pay in your own checkout on the web, you need to provide merchant validation. This is normally done using Apple’s Requesting Apple Pay Session. The merchant validation proves (to Apple) that a validated merchant is calling the Apple Pay Javascript APIs.
     * <p>
     * When integrating Apple Pay via Mollie, you cannot call Apple’s API but you should call this API instead. The response of this API call should be passed as-is to the the completion method, completeMerchantValidation.
     * <p>
     * Before requesting an Apple Pay Payment Session, you must place the domain validation file on your server at: https://[domain]/.well-known/apple-developer-merchantid-domain-association. Without this file, it will not be possible to use Apple Pay on your domain.
     * <p>
     * The guidelines for working with a payment session are:
     *
     * <ul>
     * <li>Request a new payment session object for each transaction. You can only use a merchant session object a single time.</li>
     * <li>The payment session object expires five minutes after it is created.</li>
     * <li>Never request the payment session from the browser. The request must be sent from your server.</li>
     * <li>For the full documentation, see the official Apple Pay JS API documentation.</li>
     * </ul>
     *
     * @param body ApplePaySessionRequest The validationUrl you got from the <a href="https://developer.apple.com/documentation/apple_pay_on_the_web/applepayvalidatemerchantevent"></a>ApplePayValidateMerchant</a> event.
     * @return The ApplePaySession response from mollie, which can then be passed as-is to the completion method <a href="https://developer.apple.com/documentation/apple_pay_on_the_web/applepaysession/1778015-completemerchantvalidation">completeMerchantValidation</a>.
     * @throws MollieException when something went wrong
     */
    public ApplePaySessionResponse requestApplePaySession(ApplePaySessionRequest body) throws MollieException {
            return restService.post("/wallets/applepay/sessions", body, APPLE_PAY_SESSION_RESPONSE_TYPE);
    }
}
