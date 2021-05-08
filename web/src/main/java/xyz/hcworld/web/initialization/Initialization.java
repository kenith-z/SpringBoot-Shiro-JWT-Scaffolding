package xyz.hcworld.web.initialization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import xyz.hcworld.common.constant.SystemConstant;
import xyz.hcworld.gotool.security.RsaUtils;
import xyz.hcworld.util.RedisUtil;

import java.util.Map;

/**
 * @ClassName: Initialization
 * @Author: 张红尘
 * @Date: 2021-05-08
 * @Version： 1.0
 */
@Slf4j
@Component
public class Initialization implements ApplicationRunner{
    @Autowired
    RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Zscaffold脚手架启动");
        System.out.println("          _____                    _____                    _____                    _____                    _____                    _____                   _______                   _____            _____          \n" +
                "         /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\                  /\\    \\                 /::\\    \\                 /\\    \\          /\\    \\         \n" +
                "        /::\\    \\                /::\\    \\                /::\\    \\                /::\\    \\                /::\\    \\                /::\\    \\               /::::\\    \\               /::\\____\\        /::\\    \\        \n" +
                "        \\:::\\    \\              /::::\\    \\              /::::\\    \\              /::::\\    \\              /::::\\    \\              /::::\\    \\             /::::::\\    \\             /:::/    /       /::::\\    \\       \n" +
                "         \\:::\\    \\            /::::::\\    \\            /::::::\\    \\            /::::::\\    \\            /::::::\\    \\            /::::::\\    \\           /::::::::\\    \\           /:::/    /       /::::::\\    \\      \n" +
                "          \\:::\\    \\          /:::/\\:::\\    \\          /:::/\\:::\\    \\          /:::/\\:::\\    \\          /:::/\\:::\\    \\          /:::/\\:::\\    \\         /:::/~~\\:::\\    \\         /:::/    /       /:::/\\:::\\    \\     \n" +
                "           \\:::\\    \\        /:::/__\\:::\\    \\        /:::/  \\:::\\    \\        /:::/__\\:::\\    \\        /:::/__\\:::\\    \\        /:::/__\\:::\\    \\       /:::/    \\:::\\    \\       /:::/    /       /:::/  \\:::\\    \\    \n" +
                "            \\:::\\    \\       \\:::\\   \\:::\\    \\      /:::/    \\:::\\    \\      /::::\\   \\:::\\    \\      /::::\\   \\:::\\    \\      /::::\\   \\:::\\    \\     /:::/    / \\:::\\    \\     /:::/    /       /:::/    \\:::\\    \\   \n" +
                "             \\:::\\    \\    ___\\:::\\   \\:::\\    \\    /:::/    / \\:::\\    \\    /::::::\\   \\:::\\    \\    /::::::\\   \\:::\\    \\    /::::::\\   \\:::\\    \\   /:::/____/   \\:::\\____\\   /:::/    /       /:::/    / \\:::\\    \\  \n" +
                "              \\:::\\    \\  /\\   \\:::\\   \\:::\\    \\  /:::/    /   \\:::\\    \\  /:::/\\:::\\   \\:::\\    \\  /:::/\\:::\\   \\:::\\    \\  /:::/\\:::\\   \\:::\\    \\ |:::|    |     |:::|    | /:::/    /       /:::/    /   \\:::\\ ___\\ \n" +
                "_______________\\:::\\____\\/::\\   \\:::\\   \\:::\\____\\/:::/____/     \\:::\\____\\/:::/  \\:::\\   \\:::\\____\\/:::/  \\:::\\   \\:::\\____\\/:::/  \\:::\\   \\:::\\____\\|:::|____|     |:::|    |/:::/____/       /:::/____/     \\:::|    |\n" +
                "\\::::::::::::::::::/    /\\:::\\   \\:::\\   \\::/    /\\:::\\    \\      \\::/    /\\::/    \\:::\\  /:::/    /\\::/    \\:::\\   \\::/    /\\::/    \\:::\\   \\::/    / \\:::\\    \\   /:::/    / \\:::\\    \\       \\:::\\    \\     /:::|____|\n" +
                " \\::::::::::::::::/____/  \\:::\\   \\:::\\   \\/____/  \\:::\\    \\      \\/____/  \\/____/ \\:::\\/:::/    /  \\/____/ \\:::\\   \\/____/  \\/____/ \\:::\\   \\/____/   \\:::\\    \\ /:::/    /   \\:::\\    \\       \\:::\\    \\   /:::/    / \n" +
                "  \\:::\\~~~~\\~~~~~~         \\:::\\   \\:::\\    \\       \\:::\\    \\                       \\::::::/    /            \\:::\\    \\               \\:::\\    \\        \\:::\\    /:::/    /     \\:::\\    \\       \\:::\\    \\ /:::/    /  \n" +
                "   \\:::\\    \\               \\:::\\   \\:::\\____\\       \\:::\\    \\                       \\::::/    /              \\:::\\____\\               \\:::\\____\\        \\:::\\__/:::/    /       \\:::\\    \\       \\:::\\    /:::/    /   \n" +
                "    \\:::\\    \\               \\:::\\  /:::/    /        \\:::\\    \\                      /:::/    /                \\::/    /                \\::/    /         \\::::::::/    /         \\:::\\    \\       \\:::\\  /:::/    /    \n" +
                "     \\:::\\    \\               \\:::\\/:::/    /          \\:::\\    \\                    /:::/    /                  \\/____/                  \\/____/           \\::::::/    /           \\:::\\    \\       \\:::\\/:::/    /     \n" +
                "      \\:::\\    \\               \\::::::/    /            \\:::\\    \\                  /:::/    /                                                               \\::::/    /             \\:::\\    \\       \\::::::/    /      \n" +
                "       \\:::\\____\\               \\::::/    /              \\:::\\____\\                /:::/    /                                                                 \\::/____/               \\:::\\____\\       \\::::/    /       \n" +
                "        \\::/    /                \\::/    /                \\::/    /                \\::/    /                                                                   ~~                      \\::/    /        \\::/____/        \n" +
                "         \\/____/                  \\/____/                  \\/____/                  \\/____/                                                                                             \\/____/          ~~              \n");
        if(redisUtil.keys(SystemConstant.RSA_KEY).size()<1) {
            Map<String, String> publicAndPrivateKey = RsaUtils.genKeyPair();
            redisUtil.set(SystemConstant.PUBLIC_KEY, publicAndPrivateKey.get("publicKey"));
            redisUtil.set(SystemConstant.PRIVATE_KEY, publicAndPrivateKey.get("privateKey"));
        }
    }

}
