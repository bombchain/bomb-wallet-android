package com.alphawallet.app.entity;

import android.content.Context;

import com.alphawallet.app.C;
import com.alphawallet.app.entity.tokens.Token;
import com.alphawallet.app.entity.tokens.TokenCardMeta;
import com.alphawallet.app.entity.tokens.TokenInfo;
import com.alphawallet.app.repository.entity.RealmToken;
import com.alphawallet.app.service.TokensService;
import com.alphawallet.app.ui.widget.entity.NetworkItem;
import com.alphawallet.ethereum.EthereumNetworkBase;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.alphawallet.ethereum.EthereumNetworkBase.BOMB_ID;

public class CustomViewSettings
{
    public static final long primaryChain = BOMB_ID;
    private static final String primaryChainName = C.BOMB_NETWORK_NAME;

    //You can use the settings in this file to customise the wallet appearance

    //IF you want to re-order the the way chains appear in the wallet, see this line in EthereumNetworkBase:
    //private static final List<Long> hasValue = new ArrayList<>(Arrays.asList( ...
    //... and read the comment above it

    //Ensures certain tokens are always visible, even if zero balance (see also 'showZeroBalance()' below).
    //See also lockedChains. You can also lock the chains that are displayed on.
    //If you leave the locked chains empty, the token will appear if the chain is selected
    private static final List<TokenInfo> lockedTokens = Arrays.asList(
             //new TokenInfo(String TokenAddress, String TokenName, String TokenSymbol, int TokenDecimals, boolean isEnabled, long ChainId));
            //ew TokenInfo("0xBE192A15da3D4004242DE1B215691D08A69543D1", "Wrapped BOMB", "WBOMB", 18, true, EthereumNetworkBase.BOMB_ID),
            //new TokenInfo("0xc0B8485449248c054D5e8E98c1f2cc7B4031BD4c", "USD BOMB", "USDB", 18, true, EthereumNetworkBase.BOMB_ID),
            //new TokenInfo("0x1153b8D7b968fD7CC1F4069EA6b2E8EEC4F0ad64", "ETHERIUM", "ETHB", 18, true, EthereumNetworkBase.BOMB_ID),
            //new TokenInfo("0x140F62aCCC69cb24eABdC0E00b7caaC577cA5b24", "BITCOIN", "BBTC", 18, true, EthereumNetworkBase.BOMB_ID),
            new TokenInfo("0x886a4003A7A7279A3Ab342E4BB2F11153b7318a3", "Staked BTC", "sBTC", 18, true, EthereumNetworkBase.BOMB_ID),
            new TokenInfo("0x9d0719222211c756ba0b88d64b3fca1a5f29f997", "BITCOIN", "BTCB", 18, true, EthereumNetworkBase.BOMB_ID)
            //new TokenInfo("0x522348779DCb2911539e76A1042aA922F9C47Ee3", "BOMB", "BOMB", 18, true, EthereumNetworkBase.BINANCE_MAIN_ID)
    );

    //List of chains that wallet can show
    //If blank, enable the user filter select dialog, if there are any entries here, the select network dialog is disabled
    //Note: you should always enable the chainId corresponding to the chainIDs in the lockedTokens.
    private static final List<Long> lockedChains = Arrays.asList(
           //EthereumNetworkBase.BOMB_ID
            //EthereumNetworkBase.MAINNET_ID //EG only show Main, xdai, classic and two testnets. Don't allow user to select any others
            EthereumNetworkBase.BINANCE_MAIN_ID
            //EthereumNetworkBase.RINKEBY_ID, //You can mix testnets and mainnets, but probably shouldn't as it may result in people getting scammed
            //EthereumNetworkBase.GOERLI_ID
    );

    public static final List<Long> alwaysVisibleChains = Arrays.asList(
            //EthereumNetworkBase.BOMB_ID//,
            EthereumNetworkBase.BINANCE_MAIN_ID
    );

    public static boolean alwaysShow(long chainId)
    {
        return alwaysVisibleChains.contains(chainId);
    }

    //TODO: Wallet can only show the above tokens
    private static final boolean onlyShowTheseTokens = false;

    public static List<TokenInfo> getLockedTokens()
    {
        return lockedTokens;
    }

    public static List<Long> getLockedChains()
    {
        return lockedChains;
    }

    //TODO: Not yet implemented; code will probably live in TokensService & TokenRealmSource
    public static boolean onlyShowLockedTokens()
    {
        return onlyShowTheseTokens;
    }

    //Does main wallet page show tokens with zero balance? NB: any 'Locked' tokens above will always be shown
    public static boolean showZeroBalance() { return true; }

    public static boolean tokenCanBeDisplayed(TokenCardMeta token)
    {
        return token.type == ContractType.ETHEREUM || token.isEnabled || isLockedToken(token.getChain(), token.getAddress());
    }

    private static boolean isLockedToken(long chainId, String contractAddress)
    {
        for (TokenInfo tInfo : lockedTokens)
        {
            if (tInfo.chainId == chainId && tInfo.address.equalsIgnoreCase(contractAddress)) return true;
        }

        return false;
    }

    public static ContractType checkKnownTokens(TokenInfo tokenInfo)
    {
        return ContractType.OTHER;
    }

    public static boolean showContractAddress(Token token)
    {
        return true;
    }

    public static long startupDelay()
    {
        return 0;
    }

    public static int getImageOverride()
    {
        return 0;
    }

    //Switch off dapp browser
    public static boolean hideDappBrowser()
    {
        return true;
    }

    //Hides the filter tab bar at the top of the wallet screen (ALL/CURRENCY/COLLECTIBLES)
    public static boolean hideTabBar()
    {
        return false;
    }

    //Use to switch off direct transfer, only use magiclink transfer
    public static boolean hasDirectTransfer()
    {
        return true;
    }

    //Allow multiple wallets (true) or single wallet mode (false)
    public static boolean canChangeWallets()
    {
        return true;
    }

    //Hide EIP681 generation (Payment request, generates a QR code another wallet user can scan to have all payment fields filled in)
    public static boolean hideEIP681() { return true; }

    //In main wallet menu, if wallet allows adding new tokens
    public static boolean canAddTokens() { return true; }

    //Implement minimal dappbrowser with no URL bar. You may want this if you want your browser to point to a specific website and only
    // allow navigation within that website
    // use this setting in conjunction with changing DEFAULT_HOMEPAGE in class EthereumNetworkBase
    public static boolean minimiseBrowserURLBar() { return false; }
    public static boolean minimiseEarnURLBar() { return true; }
    public static boolean minimiseStakesURLBar() { return true; }


    //public static String defaultEarnURL() {return "https://mobile-bombapp-frontend.pages.dev/mobile/positions/btc/new"; }
    public static String defaultEarnURL() {return "https://mobile-bombapp-frontend.pages.dev/#/mobile/earn"; }
    public static String defaultStakesURL() {return "https://mobile-bombapp-frontend.pages.dev/#/mobile/staked"; }
    //Allow showing token management view
    public static boolean showManageTokens() { return true; }

    //Show all networks in Select Network screen. Set to `true` to show only filtered networks.
    public static boolean showAllNetworks() { return true; }

    public static String getDecimalFormat() { return "0.####E0"; }

    public static int getDecimalPlaces() { return 5; }

    //set if the Input Amount defaults to Fiat or Crypto
    public static boolean inputAmountFiatDefault()
    {
        return false;
    }
}
