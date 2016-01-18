package cc.nfscan.server.utils;

import org.junit.Assert;
import org.junit.Test;
import static cc.nfscan.server.utils.StringUtils.*;

/**
 * Test targeting String manipulation methods used across the whole application
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
public class StringUtilsTest {

    @Test
    public void testIsNumeric() throws Exception {
        Assert.assertTrue(isNumeric("10"));
        Assert.assertFalse(isNumeric("10a"));
        Assert.assertTrue(isNumeric("6548"));
    }

    @Test
    public void testValidateCNPJ() throws Exception {
        Assert.assertTrue(validateCNPJ("22843642000195"));
        Assert.assertFalse(validateCNPJ("22843642500155"));
        Assert.assertTrue(validateCNPJ("22.843.642/0001-95"));
    }

    @Test
    public void testReverseString() throws Exception {
        Assert.assertEquals(reverseString("nfscan-receipt-donation"), "noitanod-tpiecer-nacsfn");
        Assert.assertEquals(reverseString("ljkahsldkjfhalskjdhflasjd"), "djsalfhdjkslahfjkdlshakjl");
        Assert.assertNotEquals(reverseString("ljkahsldkjfhalskjdhflasjd"), "nfscan-receipt-donation");
    }

    @Test
    public void testValidateElectronicTaxReceiptAccessKey() throws Exception {
        Assert.assertTrue(validateElectronicTaxReceiptAccessKey("35151222843642000195650010000018121400020216"));
        Assert.assertTrue(validateElectronicTaxReceiptAccessKey("3515 1222 8436 4200 0195 6500 1000 0018 1214 0002 0216"));
        Assert.assertFalse(validateElectronicTaxReceiptAccessKey("3515 1222 8436 2400 0195 6650 1000 0018 1214 0002 0216"));
        Assert.assertTrue(validateElectronicTaxReceiptAccessKey("3515 0922 8436 4200 0195 6500 1000 0000 2314 0000 2313"));
        Assert.assertFalse(validateElectronicTaxReceiptAccessKey("3500 1222 8436 2400 0195 6650 1000 0018 1214 0002 0216"));
        Assert.assertTrue(validateElectronicTaxReceiptAccessKey("3500 0922 8436 4200 0195 6500 1000 0000 2314 0000 2313"));
        Assert.assertTrue(validateElectronicTaxReceiptAccessKey("3599 0922 8436 4200 0195 6500 1000 0000 2314 0000 2313"));
        Assert.assertFalse(validateElectronicTaxReceiptAccessKey("3599 1322 8436 4200 0195 6500 1000 0000 2314 0000 2313"));
        Assert.assertFalse(validateElectronicTaxReceiptAccessKey("3599 1232 8436 4200 0195 6500 1000 0000 2314 0000 2313"));
    }

    @Test
    public void testRemoveNonNumeric() throws Exception {
        Assert.assertEquals(removeNonNumeric("3515 1222 8436 4200 0195 6500 1000 0018 1214 0002 0216"), "35151222843642000195650010000018121400020216");
        Assert.assertEquals(removeNonNumeric("22.843.642/0001-95"),"22843642000195");
    }
}