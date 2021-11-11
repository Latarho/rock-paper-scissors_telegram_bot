package game;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InlineQueryResultArticle;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;

public class Bot {
    // Инстанцируем (создаем экземпляр класса) и присваиваем его константе.
    private final TelegramBot bot = new TelegramBot(System.getenv("BOT_TOKEN"));

    /**
     * Метод - вызов листенера, опрос telegram API каждые 100 мс. Поиск сообщений боту от пользователя.
     */
    public void serve() {
        bot.setUpdatesListener(updates -> {
            // Обработка каждого сообщения от пользователя в отдельности.
            updates.forEach(this::process);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    /**
     * Метод - получение сообщения от пользователя и его обработка.
     * @param update
     */
    private void process(Update update) {
        // Инстанцируем экземпляр класса Message.
        Message message = update.message();
        // Инстанцируем экземпляр класса CallbackQuery.
        CallbackQuery callbackQuery = update.callbackQuery();
        // Инстанцируем экземпляр класса InlineQuery.
        InlineQuery inlineQuery = update.inlineQuery();

        // Инстанцируем переменную - запрос от пользователя.
        BaseRequest request = null;

        if (inlineQuery != null) {
            InlineQueryResultArticle rock = buildInlineButton("камень", "Камень", "0");
        } else if (message != null) {
            long chatId = message.chat().id();
            request = new SendMessage(chatId, "Здарова!");
        }
        // Если запрос от пользователя не пустой, то бот его принимает.
        if (request != null) {
            bot.execute(request);
        }
    }

    private InlineQueryResultArticle buildInlineButton(String id, String title, String callbackData) {
        return new InlineQueryResultArticle(id, title, "Я готов к бою!")
                .replyMarkup(
                        new InlineKeyboardMarkup(
                                new InlineKeyboardButton("Выполнение...").callbackData(callbackData)
                        )
                );
    }
}
